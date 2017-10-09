import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  date: new Date().toISOString().substring(0, 10),

  dateChanged: function () {
    this.send('dateChanged');
  }.observes('date'),

  actions: {
    dateChanged() {
      this.get('ajax').request('/admin/getAllReservations/' + this.get('model.restaurant.id') + '/' + this.get('date'), {
        xhrFields: {
          withCredentials: true,
        },
      })
      .then((result) => this.set('model.reservations', result));
    },
  },
});
