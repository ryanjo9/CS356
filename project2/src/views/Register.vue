<template>
    <div class="container">
    <br />
    <br />
    <div class="row">
        <div class="col" />
        <div class="col-6">
        <form @submit.prevent="login">
            <p class="h4 text-center mb-4">Sign up</p>
            <div class="grey-text">
                <mdbIcon icon="user" size="lg" class="icon"/>
                <mdb-input required class="input" v-model="username" placeholder="Your name" type="text"/> <br/>
                <mdbIcon icon="envelope" size="lg" class="icon"/>
                <mdb-input required class="input" v-model="email" placeholder="Your email" type="email"/> <br/>
                <mdbIcon icon="lock" size="lg" class="icon"/>
                <mdb-input required class="input" v-model="password" placeholder="Your password" type="password"/> <br/>
                <mdbIcon icon="exclamation-triangle" size="lg" class="icon"/>
                <mdb-input required class="input" v-model="recheck" placeholder="Confirm your password" type="text"/> <br/>
            </div>
            <div class="text-center">
                <mdb-btn color="success" style="height:40px;" v-if="check">Register</mdb-btn>
            </div>
        </form>
        </div>
         <div class="col" />
    </div>
    <br />
    <br />
    <br />
    </div>
</template>

<script>
import { mdbInput, mdbBtn} from 'mdbvue';
export default {
  name: 'register',
  components: {
      mdbInput,
      mdbBtn
    },
  data() {
    return {
      username: '',
      password: '',
      email: '',
      recheck:'',
      error: '',
    }
  },
  computed:{
      check(){
         return (this.password === this.recheck) ? true : false
      }
  },
  methods: {
    async login() {
      try {
        this.error = await this.$store.dispatch("login", {
          username: this.username,
          password: this.password,
            email: this.email
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
.register{
  margin-right: 10px;
}
.icon{
  margin-right: -80%;
}
.input{
  margin-top: -30px;
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