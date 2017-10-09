import Ember from 'ember';

export default Ember.Controller.extend({
  queryParams: {
    currentPage: 'page',
  },
  currentPage: 1,
});
