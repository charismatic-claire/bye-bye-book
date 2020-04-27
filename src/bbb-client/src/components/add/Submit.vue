<template>
    <div id="send-button">
        <div v-if="isValid" class="d-flex justify-content-center ms-confirm">
            <b-button variant="success" @click="submit">{{ label }}</b-button>
        </div>
        <div v-else class="d-flex justify-content-center ms-confirm">
            <b-button disabled>{{ label }}</b-button>
        </div>
    </div>

</template>

<script>
    import { MT } from "../../store/mutation-types"

    export default {
        name: "Submit",
        props: [ 'label' ],
        methods: {
            submit() {
                this.$store.dispatch(MT.SUBMIT)
            }
        },
        computed: {
            isValid() {
                return this.$store.getters.isFormValid
            }
        }
    }
</script>

<style scoped>
    .ms-confirm {
        margin-top: 50px;
    }
    .ms-confirm > button {
        margin: 5px;
    }
</style>