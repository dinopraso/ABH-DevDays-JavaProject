import Ember from 'ember';

const {
  inject: {
    service,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  actions: {
    submitCity() {
      let coordinates = this.get('polygon').getPath().map((xy) => Ember.Object.extend({ lat: xy.lat(), lng: xy.lng() }));

      this.set('model.location.bounds', JSON.stringify(coordinates));
      let jsonData = JSON.stringify(this.get('model.location'));

      let isNew = typeof this.get('model.location.id') === 'undefined';
      let method = isNew ? 'post' : 'put';
      let request = '/admin/' + (isNew ? 'createCity' : 'editCity');

      this.get('ajax')[method](request, {
        xhrFields: {
          withCredentials: true,
        },
        contentType: 'application/json',
        data: jsonData,
      })
      .then(() =>  this.transitionToRoute('admin.locations'));
    },
  },
});
