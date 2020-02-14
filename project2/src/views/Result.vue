<template>
  <div class="home">
    <!-- This is just to remind us that it's there -->
    
    <ul id="example-2">
    <span v-for="(book, position) in books" v-bind:key="position">
        <div>
        <img :src="book.imagePath"/>
        {{ book.title }} - {{book.author}}
        Condition: {{ book.condition }}
        Price: {{ book.price }}
        </div>
    </span>
    </ul>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'result',
  data() {
    return {
      books: []
    }
  },
  async created() {
    this.books = await this.$store.dispatch('getBooks');
    this.books = this.books.filter((book) => {
        let add = false;
        Object.values(book).forEach((value) => {
            if (typeof value === 'string' && value.toLowerCase().includes(this.$route.params.input)) {
                add = true
            }
        })

        return add
    })
  }
}
</script>
