<template>
    <div class="text-line-form m-3">
        <h3>{{ this.config.HEADING }}</h3>
        <b-form-input
                :placeholder="this.config.HELP"
                :type="this.config.TYPE"
                :state="isValid"
                v-model="text"
                @change="pushText">
        </b-form-input>
    </div>
</template>

<script>
    import { MT } from "../../store/mutation-types"

    export default {
        name: "TextLineForm",
        props: [ 'config', 'type', 'mutation'],
        data() {
            return {
                text: ''
            }
        },
        methods: {
            pushText() {
                this.$store.commit(this.mutation, this.text)
            }
        },
        computed: {
            isValid() {
                const text = this.text
                if (text instanceof String || typeof text === 'string') {
                    if(this.mutation === MT.SET_FROM_NAME) return text.length > 0
                        else {
                            // eslint-disable-next-line no-useless-escape
                            const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/
                            return reg.test(text)
                        }
                } else return false
            }
        }
    }
</script>