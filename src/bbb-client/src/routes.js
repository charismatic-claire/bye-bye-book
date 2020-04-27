import AddPosting from "./components/add/AddPosting"
import ShowAllPostings from "./components/show/ShowAllPostings"

export default [
    { path: '/', component: ShowAllPostings },
    { path: '/add', component: AddPosting }
]