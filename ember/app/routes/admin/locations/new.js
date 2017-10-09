import Ember from 'ember';

export default Ember.Route.extend({
  model() {
    return {
      location: {
        nwBoundLat: 43.9,
        nwBoundLong: 18.1,
        seBoundLat: 43.8,
        seBoundLong: 19.9,
      },
    };
  },
});
