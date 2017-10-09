import Ember from 'ember';

const {
  computed,
} = Ember;

export default Ember.Controller.extend({
  hasUpcomingReservations: computed('model', function () {
    return this.get('model.userReservations.upcoming').length > 0;
  }),

  hasPastReservations: computed('model', function () {
    return this.get('model.userReservations.past').length > 0;
  }),

});
