import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  actions: {
    deleteRestaurant() {
      this.get('ajax').del('/admin/deleteRestaurant/' + this.get('model.restaurant.id'), {
        xhrFields: {
          withCredentials: true,
        },
      })
      .then(
        () => this.transitionToRoute('admin.restaurants'),
        (error) =>  alert(error)
      );
    },

    cancel() {
      this.transitionToRoute('admin.restaurants');
    },
  },
});
