import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  queryParams: {
    restaurantName: 'name',
    numberOfPeople: 'people',
    date: 'date',
    time: 'time',
    currentPage: 'page',
  },
  restaurantName: '',
  numberOfPeople: 0,
  currentPage: 1,

  actions: {
    reserve(restaurantId, numberOfPeople, date, selectedTime) {
      this.get('ajax').post('/postReservation', {
        contentType: 'application/json',
        data: JSON.stringify({
          restaurantId: restaurantId,
          numberOfPeople: numberOfPeople,
          date: date,
          time: selectedTime,
        }),
      })
      .then((response) => this.transitionToRoute('reservation-details', response.id));
    },
  },
});
