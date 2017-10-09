import Ember from 'ember';
import $ from 'jquery';

const {
  inject: {
    service,
  },
  computed,
  String: {
    htmlSafe,
  },
} = Ember;

export default Ember.Controller.extend({
  ajax: service('ajax'),

  uploadProgressProfile: null,
  uploadProgressCover: null,
  trueClass: 'full',
  falseClass: 'empty',

  profileImageStyle: computed('model.restaurant.profileImagePath', function () {
    return htmlSafe('background-image: url(' + this.get('model.restaurant.profileImagePath') + ')');
  }),

  coverImageStyle: computed('model.restaurant.coverImagePath', function () {
    return htmlSafe('background-image: url(' + this.get('model.restaurant.coverImagePath') + ')');
  }),

  isRangeOne: computed('model.restaurant.priceRange', function () {
    return this.get('model.restaurant.priceRange') >= 1 ? this.get('trueClass') : this.get('falseClass');
  }),

  isRangeTwo: computed('model.restaurant.priceRange', function () {
    return this.get('model.restaurant.priceRange') >= 2 ? this.get('trueClass') : this.get('falseClass');
  }),

  isRangeThree: computed('model.restaurant.priceRange', function () {
    return this.get('model.restaurant.priceRange') >= 3 ? this.get('trueClass') : this.get('falseClass');
  }),

  isRangeFour: computed('model.restaurant.priceRange', function () {
    return this.get('model.restaurant.priceRange') >= 4 ? this.get('trueClass') : this.get('falseClass');
  }),

  cuisinePrimitives: computed('model.cuisines', function () {
    return this.get('model.cuisines').map((cuisine) => cuisine.name);
  }),

  selectedCuisinePrimitives: computed('model.restaurant.cuisines', function () {
    return this.get('model.restaurant.cuisines').map((cuisine) => cuisine.name);
  }),

  actions: {
    addTable() {
      this.get('model.restaurant.tables').pushObject({ id: null, restaurantId: this.get('model.restaurant.id'), numberOfChairs: 0 });
    },

    removeTable(tableId) {
      this.get('model.restaurant.tables').removeObject(this.get('model.restaurant.tables').findBy('id', tableId));
    },

    setCity() {
      let selectBox = document.getElementById('city-select');
      this.set('model.restaurant.city', this.get('model.cities').findBy('id', selectBox.options[selectBox.selectedIndex].value));
    },

    submitRestaurant() {
      this.set('model.restaurant.latitude', this.get('marker').getPosition().lat());
      this.set('model.restaurant.longitude', this.get('marker').getPosition().lng());

      if (this.get('marker').getPosition() === this.get('defaultMerkerPosition')) {
        return alert('Please Move the Map Marker to the correct position');
      }

      let markerLatLng = new google.maps.LatLng(this.get('model.restaurant.latitude'), this.get('model.restaurant.longitude'));
      if (!google.maps.geometry.poly.containsLocation(markerLatLng, this.get('polygon'))) {
        return alert('Marker out of Bounds. Please position the marker within the selected City.');
      }

      if (this.get('uploadProgressProfile') !== null || this.get('uploadProgressCover') !== null) {
        return alert('Please wait for upload to finish');
      }

      let selectedCuisines = this.get('selectedCuisinePrimitives').map((primitive) => this.get('model.cuisines').find((cuisine) => cuisine.name === primitive));

      this.set('model.restaurant.menu', JSON.stringify(this.get('model.restaurant.menu')));
      this.set('model.restaurant.cuisines', selectedCuisines);
      let jsonData = JSON.stringify(this.get('model.restaurant'));

      let isNew = typeof this.get('model.restaurant.id') === 'undefined';
      let method = isNew ? 'post' : 'put';
      let request = '/admin/' + (isNew ? 'createRestaurant' : 'editRestaurant');

      this.get('ajax')[method](request, {
        xhrFields: {
          withCredentials: true,
        },
        contentType: 'application/json',
        data: jsonData,
      })
      .then(() =>  this.transitionToRoute('admin.restaurants'));
    },

    priceRangeChange(value) {
      $('#pricerange-control input').each((index, element) => {
        if (element.value === 0) { return; }

        $(element).parent().css('color', element.value <= value ? '#fd6f60' : 'rgba(19, 29, 36, 0.2)');
      });
    },

    repositionRestaurantMarker(newLat, newLong) {
      this.set('model.restaurant.latitude', newLat);
      this.set('model.restaurant.longitude', newLong);
    },

    uploadedImage(imageFor, fileExtension) {
      this.get('ajax').patch('/admin/updatePicture', {
        xhrFields: {
          withCredentials: true,
        },
        data: {
          restaurantId: this.get('model.restaurant.id'),
          imageType: imageFor,
          extension: fileExtension,
        },
      })
      .then((response) => this.set(response.imageFor === 'profile' ? 'model.restaurant.profileImagePath' : 'model.restaurant.coverImagePath', response.url));
    },

    addMenuBreakfast() {
      let menu = this.get('model.restaurant.menu.breakfast');
      if (typeof menu === 'undefined') {
        this.set('model.restaurant.menu', {});
        this.set('model.restaurant.menu.breakfast', []);
        this.set('model.restaurant.menu.lunch', []);
        this.set('model.restaurant.menu.dinner', []);
      }

      this.get('model.restaurant.menu.breakfast').pushObject({ id: null, name: '', description: '', price: 0 });
    },

    addMenuLunch() {
      let menu = this.get('model.restaurant.menu.lunch');
      if (typeof menu === 'undefined') {
        this.set('model.restaurant.menu', {});
        this.set('model.restaurant.menu.breakfast', []);
        this.set('model.restaurant.menu.lunch', []);
        this.set('model.restaurant.menu.dinner', []);
      }

      this.get('model.restaurant.menu.lunch').pushObject({ id: null, name: '', description: '', price: 0 });
    },

    addMenuDinner() {
      let menu = this.get('model.restaurant.menu.dinner');
      if (typeof menu === 'undefined') {
        this.set('model.restaurant.menu', {});
        this.set('model.restaurant.menu.breakfast', []);
        this.set('model.restaurant.menu.lunch', []);
        this.set('model.restaurant.menu.dinner', []);
      }

      this.get('model.restaurant.menu.dinner').pushObject({ id: null, name: '', description: '', price: 0 });
    },

    removeBreakfastItem(item) {
      this.get('model.restaurant.menu.breakfast').removeObject(item);
    },

    removeLunchItem(item) {
      this.get('model.restaurant.menu.lunch').removeObject(item);
    },

    removeDinnerItem(item) {
      this.get('model.restaurant.menu.dinner').removeObject(item);
    },
  },

});
