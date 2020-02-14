<template>
  <div >
    <div v-if="searchDone">
      <Search v-on:searchDone="switchView"/>
    </div>
    <div v-else>
      <Form v-on:cancel="switchView" :bookData.sync="bookData"/>
    </div>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
  </div>
</template>

<script>
import Search from '@/components/Search.vue'
import Form from '@/components/Form.vue'
export default {
  name: 'Add',
  components: {
    Search,
    Form
  },
  data() {
    return {
      searchDone: this.$store.bookData !== null,
      bookData: null
    }
  },
  methods: {
    switchView(data) {
      console.log('switching view')
      this.bookData = data
      this.searchDone = !this.searchDone
    }
  }
}
</script>

<style scoped>
body {
  /* The image used */
  background-color: black;
  /* Add the blur effect */
  filter: blur(8px);
  -webkit-filter: blur(8px);
}

/* Position text in the middle of the page/image */
.bg-text {
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0, 0.4); /* Black w/opacity/see-through */
  color: white;
  font-weight: bold;
  border: 3px solid #f1f1f1;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 2;
  width: 80%;
  padding: 20px;
  text-align: center;
}
</style>
