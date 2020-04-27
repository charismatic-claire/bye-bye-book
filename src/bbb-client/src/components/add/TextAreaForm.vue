<template>
    <div class="text-area-form m-3">
        <h3>{{ this.config.HEADING }}</h3>
        <p>{{ this.config.HELP }}</p>
        <b-form-textarea
                :placeholder="placeholder"
                :state="isValid"
                v-model="text"
                @change="pushText">
        </b-form-textarea>
    </div>
</template>

<script>
    import { LABELS } from "../../assets/labels"
    import { MT } from "../../store/mutation-types"

    export default {
        name: "TextAreaForm",
        props: [ 'config' ],
        data() {
            return {
                placeholder: LABELS.STORY.PLACEHOLDER,
                text: ''
            }
        },
        methods: {
            pushText() {
                const newStory = {
                    title: this.config.ID,
                    body: this.text
                }
                this.$store.commit(MT.SET_STORY, newStory)
            }
        },
        computed: {
            isValid() {
                const text = this.text
                if (text instanceof String || typeof text === 'string') return text.length > 0
                else return false
            }
        }
    }
</script>