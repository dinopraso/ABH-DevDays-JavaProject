import Ember from 'ember';

const {
  computed,
} = Ember;

export default Ember.Component.extend({
  trueClass: 'full',
  falseClass: 'empty',
  hasOneStar: computed('averageRating', function () {
    return this.get('averageRating') >= 0.5 ? this.get('trueClass') : this.get('falseClass');
  }),

  hasTwoStars: computed('averageRating', function () {
    return this.get('averageRating') >= 1.5 ? this.get('trueClass') : this.get('falseClass');
  }),

  hasThreeStars: computed('averageRating', function () {
    return this.get('averageRating') >= 2.5 ? this.get('trueClass') : this.get('falseClass');
  }),

  hasFourStars: computed('averageRating', function () {
    return this.get('averageRating') >= 3.5 ? this.get('trueClass') : this.get('falseClass');
  }),

  hasFiveStars: computed('averageRating', function () {
    return this.get('averageRating') >= 4.5 ? this.get('trueClass') : this.get('falseClass');
  }),

});
