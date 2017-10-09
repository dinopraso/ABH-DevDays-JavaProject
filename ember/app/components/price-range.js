import Ember from 'ember';

const {
  computed,
} = Ember;

export default Ember.Component.extend({
  trueClass: 'full',
  falseClass: 'empty',
  isRangeOne: computed('priceRange', function () {
    return this.get('priceRange') >= 1 ? this.get('trueClass') : this.get('falseClass');
  }),

  isRangeTwo: computed('priceRange', function () {
    return this.get('priceRange') >= 2 ? this.get('trueClass') : this.get('falseClass');
  }),

  isRangeThree: computed('priceRange', function () {
    return this.get('priceRange') >= 3 ? this.get('trueClass') : this.get('falseClass');
  }),

  isRangeFour: computed('priceRange', function () {
    return this.get('priceRange') >= 4 ? this.get('trueClass') : this.get('falseClass');
  }),

});
