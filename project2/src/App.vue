<template>
  <div id="app">
    <div id="nav">
      <!-- <h1 style="width:10px">Usedbooks.com</h1> -->
      <nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="background-color: #232F3E">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav mr-auto">
            <img src="./assets/hiclipart.com.png" alt="logo" style="width:50px;">
            <li class="nav-item">
              <h3 style="color:white;padding-left:10px;padding-right:10px;">Usedbooks.com</h3>
            </li>
            <li class="nav-item active">
              <router-link style="color:white" class="nav-link" to="/">Home</router-link>
            </li>
            <li class="nav-item">
              <router-link style="color:white" class="nav-link" to="/add">Add</router-link>
            </li>
            <li class="nav-item">
              <router-link style="color:white" class="nav-link" to="/login">Login </router-link>
            </li>
            <li class="nav-item">
              <router-link style="color:white" class="nav-link" to="/register">Register </router-link>
            </li>
          </ul>
          <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" minlength="3"  type="search" placeholder="Search" aria-label="Search" v-model="input">
            <button class="btn btn-outline-info my-2 my-sm-0" @click="search" type="submit">Submit</button>
          </form>
        </div>
      </nav>
      <router-view/>
      <nav class="navbar navbar-expand-lg navbar-dark bg-secondary">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
          <ul class="navbar-nav mr-auto">
          </ul>
          <button class="bg-white">
            <a href="#app"> <i class="material-icons">arrow_upward</i> </a>
          </button>
        </div>
      </nav>
    </div>
  </div>
</template>

<script>
export default {
  name: 'app',
  data() {
    return {
      input: ''
    }
  },
  methods: {
    search() {
      if (this.input.length < 3) {
        return
      }
      this.$router.push({ name: 'result', params: { input: this.input }}) 
    }
  }
}
</script>

<style>
.navbar{
  background-color: #232F3E;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #F5FAFA;
}
#nav {
  padding: 30px;
}
#nav a {
  font-weight: bold;
  color: #2c3e50;
}
.bg-light{
background-color: #717477;
}
</style>