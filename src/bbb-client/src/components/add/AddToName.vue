<template>
    <div class="to-name-form m-3">
        <h3>{{ config.HEADING }}</h3>
        <b-form-select
                @change="pushText"
                v-model="text"
                :options="options"
                :state="isValid"/>
    </div>
</template>

<script>
    import { MT } from "../../store/mutation-types"

    export default {
        name: "AddToName",
        props: [ 'config' ],
        data() {
            const help = {
                value: null,
                text: this.config.HELP
            }
            const subjects = this.config.SUBJECTS.map(s => ({
                value: s,
                text: s
            }))
            return {
                options: [help, ...subjects],
                text: null
            }
        },
        methods: {
            pushText() {
                this.$store.commit(MT.SET_TO_NAME, this.text)
            }
        },
        computed: {
            isValid() {
                if(this.text instanceof String || typeof this.text === 'string') return this.text.length > 0
                else return false
            }
        }
    }
</script>
