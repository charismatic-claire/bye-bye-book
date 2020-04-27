<template>
    <div class="show-post">
        <div class="bg-dark p-3">
            <!-- <h1>#{{ posting.id }}: Was {{ posting.fromName }} über {{ posting.toName }} sagt</h1> -->
            <h1>#{{ posting.id }}: What {{ posting.fromName }} says about {{ posting.toName }}</h1>
        </div>
        <b-img v-for="imageUri in posting.imageUris"
               :key="'img_' + imageUri.id"
               :src="getUrl(imageUri)"
                fluid
                class="mb-3"/>
        <ShowStory v-for="story in posting.stories"
                   :key="'story_' + story.id"
                   :story="story"/>
        </div>
</template>

<script>
    import { LABELS } from "../../assets/labels"
    import ShowStory from "./ShowStory"

    export default {
        name: "ShowPosting",
        components: {
            ShowStory
        },
        props: [ 'posting' ],
        data() { return { LABELS } },
        methods: {
            getUrl(imageUri) {
                return process.env.VUE_APP_BASE_URL + imageUri.url
            }
        }
    }
</script>

<style scoped>
    .show-post>div>h1 {
        color: azure;
    }
</style>