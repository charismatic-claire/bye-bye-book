<template>
    <div id="add-posting">
        <div v-if="getNewPosting">
            <ShowPosting :posting="getNewPosting"/>
        </div>
        <div v-else>
            <TextLineForm
                    :config="LABELS.FROM_NAME"
                    :mutation="MT.SET_FROM_NAME"
                    type="text"/>
            <TextLineForm
                    :config="LABELS.FROM_EMAIL"
                    :mutation="MT.SET_FROM_EMAIL"
                    type="email"/>
            <AddToName :config="LABELS.TO_NAME"/>
            <ImageForm :config="LABELS.IMAGE"/>
            <TextAreaForm :config="LABELS.STORY.WANNA_DO_NEVER_DID"/>
            <TextAreaForm :config="LABELS.STORY.SURPRISE"/>
            <TextAreaForm :config="LABELS.STORY.LIKED"/>
            <TextAreaForm :config="LABELS.STORY.DISLIKED"/>
            <TextAreaForm :config="LABELS.STORY.WILL_MISS"/>
            <TextAreaForm :config="LABELS.STORY.SECRET_TO_SHARE"/>
            <TextAreaForm :config="LABELS.STORY.GOODBYE"/>
            <AddGeneralTerms :config="LABELS.GENERAL_TERMS_CONDITIONS"/>
            <Submit :label="LABELS.SUBMIT"/>
        </div>
    </div>
</template>

<script>
    import TextLineForm from "./TextLineForm"
    import ImageForm from "./ImageForm"
    import Submit from "./Submit"
    import TextAreaForm from "./TextAreaForm"
    import AddGeneralTerms from "./AddGeneralTerms"
    import AddToName from "./AddToName"
    import ShowPosting from "../show/ShowPosting"
    import { MT  } from "../../store/mutation-types"
    import { LABELS } from "../../assets/labels"

    export default {
        name: "AddPosting",
        components: {
            ShowPosting,
            TextLineForm,
            AddToName,
            ImageForm,
            TextAreaForm,
            AddGeneralTerms,
            Submit
        },
        data() { return { MT, LABELS } },
        computed: {
            getNewPosting() {
                const obj = this.$store.getters.getNewPosting
                if(Object.keys(obj).length === 0 && obj.constructor === Object) return null
                else return obj
            }
        }
    }
</script>
