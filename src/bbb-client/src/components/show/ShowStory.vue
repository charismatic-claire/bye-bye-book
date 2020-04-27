<template>
    <div class="show-story" :class="getStoryClass()">
        <h3>{{ getHeading() }}</h3>
        <p>{{ story.body }}</p>
    </div>
</template>

<script>
    import { LABELS } from "../../assets/labels"

    export default {
        name: "ShowStory",
        props: [ 'story' ],
        methods: {
            getStoryTitle() {
                return this.story.title
            },
            getStoryClass() {
                return String(this.getStoryTitle()).toLowerCase().replace('_', '-')
            },
            getHeading() {
                return LABELS.STORY[this.getStoryTitle()].HEADING
            }
        }
    }
</script>

<style scoped>

</style>