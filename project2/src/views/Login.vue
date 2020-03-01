<template>
    <div class="container">
      <br />   <br />
        <div  class="row">
          <div class="col" />
          <div class="col-6">
          <form @submit.prevent="login" class="pure-form pure-form-aligned">
              <p class="h4 text-center mb-4">Login to your Account</p>
              <mdbIcon icon="user" size="lg" class="icon"/>
              <mdb-input required class="input" type="text" v-model="username" placeholder="Username"/><br />
              <mdbIcon icon="lock" size="lg" class="icon"/>
              <mdb-input required class="input" type="password" v-model="password" placeholder="Password"/> <br />
              <div class="text-center">
                <mdb-btn color="primary" type="submit" class="register">Submit</mdb-btn>
                <router-link to="/register"><mdb-btn color="success" type="submit" style="height:40px;">Register</mdb-btn></router-link>
              </div>
              <p class="pure-form-message-inline" style="color:red"> * All fields are required.</p>
          </form>
          <p v-if="error" class="error">{{error}}</p>
          </div>
        <div class="col" />
      </div>
      <br />   <br />
      <br />   <br />
      <br />   <br />
    </div>
</template>

<script>
import { mdbInput, mdbBtn, mdbIcon} from 'mdbvue';
export default {
  name: 'login',
  components: {
      mdbInput,
      mdbBtn,
      mdbIcon
    },
  data() {
    return {
      username: '',
      password: '',
      error: '',
    }
  },
  methods: {
    async login() {
      try {
        this.error = await this.$store.dispatch("login", {
          username: this.username,
          password: this.password
        });
        if (this.error === "")
          this.$router.push('/');
      } catch (error) {
        console.log(error);
      }
    }
  }
}
</script>

<style scoped>
.icon{
  margin-right: -80%;
}
.input{
  margin-top: -30px;
}
.register{
  height:40px;
  margin-right: 10px;
}
form {
  border: 1px solid #ccc;
  background-color: #eee;
  border-radius: 4px;
  padding: 20px;
}
.pure-controls {
  display: flex;
}
.pure-controls button {
  margin-left: auto;
}
</style>