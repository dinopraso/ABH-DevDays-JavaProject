import Ember from 'ember';

const {
  computed,
  String: {
    htmlSafe,
  },
} = Ember;

export default Ember.Component.extend({
  navigationStyle: computed('coverImage', function () {
    return htmlSafe('background-image: url(' + this.get('coverImage') + ')');
  }),
});
