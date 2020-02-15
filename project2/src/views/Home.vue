<template>
  <div class="home">
    <!-- This is just to remind us that it's there -->
    <p v-if="username">Welcome {{ username }} </p>
    
    <ul id="example-3">
      <li v-for="(category, index) in categories" v-bind:key="index">
        <h1>{{ index }}</h1>
        <div>
          <ul id="example-2">
            <span v-for="(book, position) in category" v-bind:key="position">
              <section class="box">
                <img  class="picture" :src="book.imagePath"/>
                <span>{{ book.title }} - Author: {{book.author}} - Condition: {{ book.condition }}- Price: {{ book.price }}
              </span>
              </section>
            </span>
          </ul>
        </div>
        <br>
      </li>
    </ul>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'home',
  data() {
    return {
      username: this.$store.state.username
    }
  },
  computed: {
    // a computed getter
    books() {
      // `this` points to the vm instance
      return this.$store.state.books
    },
    categories(){
      return this.$store.state.categories
    }
  },
  async created() {
    await this.$store.dispatch('getBooks');
  }
}
</script>
<style scoped>
.picture{
  width:150px;
}
.box{
  display: flex;
}
</style>
