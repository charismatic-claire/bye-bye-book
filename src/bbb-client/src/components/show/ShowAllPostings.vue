<template>
    <div id="show-all-postings">
        <ShowPosting v-for="posting in getPostings" :key="posting.id" :posting="posting"/>
    </div>
</template>

<script>
    import ShowPosting from "./ShowPosting"
    import { MT } from "../../store/mutation-types"

    export default {
        name: "ShowAllPostings",
        components: { ShowPosting },
        computed: {
            getPostings() {
                return this.$store.getters.getPostings
            }
        },
        beforeMount() {
            this.$store.dispatch(MT.SET_POSTINGS)
        }
    }
</script>

<style scoped>

</style>