import Ember from 'ember';

const {
  inject: {
    service,
  },
  computed,
} = Ember;

export default Ember.Component.extend({
  ajax: service('ajax'),

  inquiryResponse: computed('id', 'params', function () {
    this.get('ajax').post('/postReservationInquiry', {
      data: {
        restaurantId: this.get('id'),
        numberOfPeople: this.get('params').numberOfPeople,
        date: this.get('params').date,
        time: this.get('params').time,
      },
    })
    .then(
      (response) => this.set('inquiryResponse', response),
      (error) => {
        this.set('hasError', true);
        this.set('errorMessage', error.errors[0].title);
      }
    );
  }),

  actions: {
    reserve(selectedTime) {
      this.sendAction('onReserve', this.get('id'), this.get('params').numberOfPeople, this.get('params').date, selectedTime);
    },
  },
});
