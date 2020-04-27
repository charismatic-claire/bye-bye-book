<template>
    <div class="image-form m-3">
        <h3>{{ this.config.HEADING }}</h3>
        <p>{{ this.config.HELP }}</p>
        <b-form-file
                :placeholder="this.config.PLACEHOLDER"
                :drop-placeholder="this.config.DROP_PLACEHOLDER"
                :browse-text="this.config.BROWSE"
                :state="isValid"
                v-model="files"
                accept="image/jpeg, image/png, image/gif"
                multiple
                required
                @input="setUrlsAndPush"
        ></b-form-file>
        <b-container fluid class="pt-3 pl-3 pr-3 bg-dark mt-3 rounded" v-if="isValid">
            <b-row>
                <b-col md="6" v-for="url in urls" :key="url.key" class="mb-3">
                    <b-img :src="url" fluid thumbnail></b-img>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    import { MT } from "../../store/mutation-types";

    export default {
        name: "ImageForm",
        props: [ 'config' ],
        data() {
            return {
                files: null,
                urls: []
            }
        },
        computed: {
            isValid() {
                if(Array.isArray(this.urls)) return this.urls.length > 0 && this.urls.length < 5
                return false
            }
        },
        methods: {
            setUrlsAndPush() {
                const files = this.files
                const urls = Array.from(files).map(file => URL.createObjectURL(file))
                if(Array.isArray(urls)) {
                    if(urls.length > 0 && urls.length < 5) {
                        this.urls = urls
                        this.$store.commit(MT.SET_IMAGE, {files, urls})
                    } else {
                        this.files = null
                        this.urls = []
                    }
                }
            }
        }
    }
</script>