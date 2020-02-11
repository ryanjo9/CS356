<template>
  <div class="home">
    <!-- This is just to remind us that it's there -->
    <p v-if="username">Welcome {{ username }} </p>
    <!-- <HelloWorld msg="Usedbooks.com"/> -->
    <ul id="example-2">
      <li v-for="(book, index) in books" v-bind:key="index">
        {{ book.author }} - {{ index }} - {{ book.title }}
      </li>
    </ul>


    <ul id="example-3">
      <li v-for="(category, index) in categories" v-bind:key="index">
        {{ category }} - {{ index }}
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
      username: this.$store.state.username,
      books: this.$store.state.books,
    }
  },
  computed: {
    categories: () => {
      const categories = {}

      this.books.forEach((book) => {
        if (categories[book.category]) {
          categories[book.category].push(book)
        } else {
          categories[book.category] = [book]
        }
      })

      return categories
    }
  },
  // components: {
  //   HelloWorld
  // },
  async created() {
    await this.$store.dispatch('getBooks');
  }
}
</script>
