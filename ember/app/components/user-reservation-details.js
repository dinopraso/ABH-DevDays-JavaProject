import Ember from 'ember';

const {
  inject: {
    service,
  },
  computed,
} = Ember;

export default Ember.Component.extend({
  ajax: service('ajax'),

  restaurant: computed('restaurant', function () {
    return this.get('ajax').request('/getRestaurant/' + this.get('reservation.table.restaurantId')).then((response) => this.set('restaurant', response));
  }),
});
