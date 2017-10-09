import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Route.extend({
  ajax: service('ajax'),

  model(params) {
    return Ember.RSVP.hash({
      restaurant: this.get('ajax').request('/getRestaurant/' + params.restaurant_id),
      user: this.get('ajax').request('/getCurrentUser', {
        xhrFields: {
          withCredentials: true,
        },
      }),
    });
  },
});
