import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  cityId: '',

  actions: {
    setCity() {
      let selectBox = document.getElementById('city-select');
      this.set('cityId', selectBox.options[selectBox.selectedIndex].value);
    },

    register() {
      var response = this.get('ajax').post('/register', {
        xhrFields: {
          withCredentials: true,
        },
        data: {
          firstName: this.get('first_name'),
          lastName: this.get('last_name'),
          email: this.get('email'),
          password: this.get('password'),
          address: this.get('address'),
          phone: this.get('phone_number'),
          cityId: this.get('cityId'),
        },
      });
      response.then(
        () => this.transitionToRoute('index'),
        (error) => {
          this.set('hasError', true);
          this.set('errorMessage', error.errors[0].title);
        }
      );
    },
  },
});
