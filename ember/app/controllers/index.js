import Ember from 'ember';

const {
  inject: {
    service,
  },
  computed: {
    notEmpty,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  restaurant_name: '',
  number_of_people: 2,

  hasPopularRestaurants: notEmpty('model.popularRestaurants'),
  geolocation: navigator.geolocation,

  time: '17:30',
  date: new Date().toISOString().substring(0, 10),

  nearbyRestaurants: function () {
    if (this.get('geolocation')) {
      this.get('geolocation').getCurrentPosition(
        (coordinates) => {
          this.get('ajax')
          .request('/getNearbyRestaurants/' + coordinates.coords.latitude + '/' + coordinates.coords.longitude)
          .then(result => this.set('nearbyRestaurants', result));
        }, () => this.set('geolocation', false)
      );
    }
  }.property('nearbyRestaurants'),

  actions: {
    findTable() {
      let filters = {
        name: this.get('restaurant_name'),
        people: this.get('number_of_people'),
        date: this.get('date'),
        time: this.get('time'),
      };
      this.transitionToRoute('search-results', { queryParams: filters });
    },

    setNumberOfPeople() {
      let selectBox = document.getElementById('numberOfPeople');
      this.set('number_of_people', selectBox.options[selectBox.selectedIndex].value);
    },
  },

});
