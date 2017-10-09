import Ember from 'ember';

export default Ember.Route.extend({
  ajax: Ember.inject.service(),
  model(params) {
    return Ember.RSVP.hash({
      restaurant: this.get('ajax').request('/getRestaurant/' + params.restaurant_id),
      cities: this.get('ajax').request('/getAllCities'),
      cuisines: this.get('ajax').request('/getAllCuisines'),
      isEdit: true,
    });
  },

  renderTemplate: function (controller, model) {
    model.restaurant.menu = JSON.parse(model.restaurant.menu);
    this.render('admin.restaurants.new', {
        model: model,
      });
  },
});
