<template>
  <div class="home">
    <!-- This is just to remind us that it's there -->
    <p v-if="username">Welcome {{ username }} </p>
    <div class="box1">
      <div class="list-group">
        <a v-bind:href="'#'+list" v-for="(list, key) in data" :key="key" class="list-group-item list-group-item-action">{{list}}</a>
      </div>
    </div>
    
    <ul id="example-3">
      <section v-for="(category, index) in data" :key="index">
        <h1 v-bind:id="category"> {{ category }}</h1>
        <div>
          <ul id="example-2">
            <span class="list">
              <list v-for="(book, position) in categories[category]" v-bind:key="position">
                <router-link :to="{ name: 'edit', params: {id: book._id, book} }">
                    <img  class="picture" :src="book.imagePath"/>
                    <section class="box2">
                      <h1 style="font-size:15px">{{ book.title }} </h1>  
                      <h2 style="font-size:9px"> Author: {{book.author}} </h2> 
                      <h3 style="font-size:9px"> Condition: {{ book.condition }}</h3>
                      <h4 style="font-size:9px">Price: {{ book.price }} </h4>
                    </section>
                </router-link>
              </list>
            </span>
          </ul>
        </div>
        <br>
      </section>
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
      data:["Action and adventure","Children's","Biography","Diary","Health","History","Documentary",
      "Mystery","Religious","Comic book","Drama","Fairytale","Poetry","Romance","Fiction & Fantasy","Psychology",
      "Horror","Technology","Politics","Thriller","Mathematics","Cookbook","Literature Reviews","Art","Science","Memoir"].sort()
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
  height: 200px;
  padding: 5px;
  position: relative;
  margin-left: 5px;
  display: flex;
}
.box1{
  float: left;
  width: 25%;
  padding: 10px;
}
.box2{
  position: static;
  display: table;
  width:150px;
  height: 180px;
}
.list{
  display: flex;
  align-items: center;
  flex-flow: row nowrap;
  overflow: auto;
} 
.left-indicator,
.right-indicator {
    visibility: hidden;
    width: 25px;
  }
</style>
