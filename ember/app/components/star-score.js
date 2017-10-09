import Ember from 'ember';

const {
  computed,
} = Ember;

export default Ember.Component.extend({
  trueClass: 'full',
  falseClass: 'empty',
  hasOneStar: computed('averageRating', function () {
    return this.get('averageRating') >= 0.25 ? this.get('trueClass') : this.get('falseClass');
  }),

  hasTwoStars: computed('averageRating', function () {
    return this.get('averageRating') >= 2 ? this.get('trueClass') : this.get('falseClass');
  }),

  hasThreeStars: computed('averageRating', function () {
    return this.get('averageRating') >= 3 ? this.get('trueClass') : this.get('falseClass');
  }),

  hasFourStars: computed('averageRating', function () {
    return this.get('averageRating') >= 4 ? this.get('trueClass') : this.get('falseClass');
  }),

  hasFiveStars: computed('averageRating', function () {
    return this.get('averageRating') >= 4.75 ? this.get('trueClass') : this.get('falseClass');
  }),

});
