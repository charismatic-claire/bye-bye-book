<template>
    <div class="gtc-form ml-3 mr-3 mt-5 mb-5">
        <b-form-checkbox
                name="gtc-checkbox"
                :value="valueChecked"
                :unchecked-value="valueUnchecked"
                v-model="status"
                @input="pushStatus">
            {{ this.config.HELP }}
            <span id="gtc-area">
                <img src="../../assets/info.png"/>
            </span>
        </b-form-checkbox>
        <b-popover target="gtc-area" triggers="hover" placement="top">
            <template v-slot:title>{{ config.POPOVER_TITLE }}</template>
            {{ config.POPOVER_BODY }}
        </b-popover>
    </div>
</template>

<script>
    import { MT } from "../../store/mutation-types";

    export default {
        name: "AddGeneralTerms",
        props: [ 'config' ],
        data() {
            return {
                status: 'not_accepted',
                valueChecked: 'accepted',
                valueUnchecked: 'not_accepted'
            }
        },
        methods: {
            pushStatus() {
                this.$store.commit(MT.SET_GTC, this.status === this.valueChecked)
            }
        }
    }
</script>

<style scoped>
    #gtc-area>img {
        with: 20px;
        height: 20px;
    }
</style>
