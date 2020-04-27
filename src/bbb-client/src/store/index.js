import Vue from 'vue'
import Vuex from 'vuex'
import { MT } from './mutation-types'
import axios from 'axios'
import _ from 'lodash'


/** SETUP VUEX ROUTER ----------------------------------------------------------------------------------------------- */
Vue.use(Vuex)
const POSTINGS_URL = process.env.VUE_APP_BASE_URL + '/postings'
const IMAGE_UPLOAD_URL = process.env.VUE_APP_BASE_URL + '/image-upload'

/** HELPER FUNCTIONS ------------------------------------------------------------------------------------------------ */
const isNonEmptyString = (x) => {
    if(x instanceof String || typeof x === 'string') return x.length > 0
    else return false
}

const isNonEmptyObject = (x) => {
    return Object.entries(x).length > 0 && x.constructor === Object
}

const isNonEmptyArray = (x) => {
    if(Array.isArray(x)) return x.length > 0
}

const isNonEmptyArrayMax4 = (x) => {
    return isNonEmptyArray(x) && x.length < 5
}

const isValidEmail = (x) => {
    // eslint-disable-next-line no-useless-escape
    const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/
    return isNonEmptyString(x) && reg.test(x)
}

const getStoryByTitle = (stories, title) => {
    const filtered = Array.from(stories).filter(story => story.title === title)
    if(filtered.length > 0) return filtered[0]
    else return null
}


/** STATE ----------------------------------------------------------------------------------------------------------- */
const getEmptyAddedObject = () => {
    return {
        fromName: '',
        fromEmail: '',
        toName: null,
        image: {
            files: null,
            urls: []
        },
        stories: [],
        acceptGtc: false
    }
}

const getDefaultState = () => {
    return {
        added: getEmptyAddedObject(),
        newPosting: {},
        postings: []
    }
}


/** MUTATIONS ------------------------------------------------------------------------------------------------------- */
const getMutations = () => {
    return {
        [MT.SET_FROM_NAME] (state, text) {
            if(isNonEmptyString(text)) state.added.fromName = text
            else state.added.fromName = ''
        },
        [MT.SET_FROM_EMAIL] (state, text) {
            if(isValidEmail(text)) state.added.fromEmail = text
            else state.added.fromEmail = ''
        },
        [MT.SET_TO_NAME] (state, text) {
            if(isNonEmptyString(text)) state.added.toName = text
            else state.added.toName = null
        },
        [MT.SET_IMAGE] (state, object) {
            const urls = object.urls
            const files = object.files
            if(isNonEmptyArrayMax4(urls)) {
                state.added.image.urls = urls
                state.added.image.files = files
            } else {
                state.added.image.urls = []
                state.added.image.files = null
            }
        },
        [MT.SET_STORY] (state, object) {
            const stories = state.added.stories
            const newStory = object
            const oldStory = getStoryByTitle(stories, newStory.title)
            const filtered = stories.filter(story => !(_.isEqual(story, oldStory)))
            if(isNonEmptyString(newStory.body)) state.added.stories = [ ...filtered, newStory ]
            else state.added.stories = filtered
        },
        [MT.SET_GTC] (state, bool) {
            state.added.acceptGtc = bool
        },
        [MT.SET_POSTINGS] (state, postings) {
            if(isNonEmptyArray(postings)) state.postings = postings
            else state.postings = []
        },
        [MT.SUBMIT] (state, posting) {
            state.newPosting = posting
            state.added = getEmptyAddedObject()
        },
        [MT.RESET] (state) {
            Object.assign(state, getDefaultState())
        }
    }
}


/** ACTIONS --------------------------------------------------------------------------------------------------------- */
const getActions = () => {
    return {
        [MT.SET_POSTINGS] (context) {
            axios.get(POSTINGS_URL)
                .then(response => response.data)
                .then(postings => context.commit(MT.SET_POSTINGS, postings))
        },
        [MT.SUBMIT] (context) {
            const rawPosting = context.state.added
            // store the images first
            const files = rawPosting.image.files
            const imageUriPromises = files
                ? Array.from(files).map(file => {
                // create form data
                const formData = new FormData()
                formData.append('file', file)
                const config = { headers: { 'Content-Type': 'multipart/form-data' } }
                // return promise
                return axios.post(IMAGE_UPLOAD_URL, formData, config)
                    .then(response => response.data)
            }) : [] //allow storing the posting even if no image exists
            // store the posting afterwards
            Promise.all(imageUriPromises)
                .then(imageUriPromise => {
                    // create posting
                    const posting = {
                        fromName: rawPosting.fromName,
                        fromEmail: rawPosting.fromEmail,
                        toName: rawPosting.toName,
                        stories: rawPosting.stories,
                        imageIds: imageUriPromise.map(uri => uri.id)
                    }
                    // return promise
                    return axios.post(POSTINGS_URL, posting)
                        .then(response => response.data) })
                        .then(posting => context.commit(MT.SUBMIT, posting))
        }
    }
}

/** GETTERS --------------------------------------------------------------------------------------------------------- */
const getGetters = () => {
    return {
        isComplete: state => {
            return isNonEmptyObject(state.newPosting)
        },
        isFormValid: state => {
            const nonEmptyArray = [
                state.added.fromName,
                state.added.toName,
                ...state.added.image.urls
            ]
            const nonEmptyBoolean = nonEmptyArray.map(text => isNonEmptyString(text))
                .reduce((acc, element) => acc && element, true)
            return nonEmptyBoolean
                && state.added.stories.length === 7
                && isValidEmail(state.added.fromEmail)
                && state.added.acceptGtc
        },
        getNewPosting: state => {
            return state.newPosting
        },
        getPostings: state => {
            return state.postings
        }
    }
}


/** EXPORTED MODULE ------------------------------------------------------------------------------------------------- */
export default new Vuex.Store({
    state: getDefaultState(),
    mutations: getMutations(),
    actions: getActions(),
    getters: getGetters()
})