import Ember from 'ember';
import $ from 'jquery';

export default Ember.Controller.extend({
  queryParams: {
    currentPage: 'page',
    nameFilter: 'name',
    priceFilter: 'price',
    ratingFilter: 'rating',
    cuisineFilter: 'cuisine',
    sortBy: 'sortBy',
    city: 'cityFilter',
  },
  currentPage: 1,
  nameFilter: '',
  priceFilter: 0,
  ratingFilter: 0,
  cuisineFilter: [],
  sortBy: 'name',
  city: '',

  actions: {
    showPopover(openSelector, closeSelector) {
      let $elementToClose = $(closeSelector);
      $elementToClose.addClass('hidden');

      let $elementToOpen = $(openSelector);
      if ($elementToOpen.hasClass('hidden')) {
        $elementToOpen.removeClass('hidden');
      } else {
        $elementToOpen.addClass('hidden');
      }
    },

    showFilters() {
      this.send('priceFilterChanged', this.get('priceFilter'));
      this.send('ratingFilterChanged', this.get('ratingFilter'));

      this.send('showPopover', '#searchFilters', '#searchSort');
    },

    showSort() {
      this.send('showPopover', '#searchSort', '#searchFilters');
    },

    radioFilterChanged(selector, value) {
      $(selector).each(function (index, element) {
        if (element.value === 0) { return; }

        $(element).parent().css('color', element.value <= value ? '#fd6f60' : 'rgba(19, 29, 36, 0.2)');
      });
    },

    priceFilterChanged(value) {
      this.send('radioFilterChanged', '#price-filter-control input', value);
    },

    ratingFilterChanged(value) {
      this.send('radioFilterChanged', '#rating-filter-control input', value);
    },

    removeNameFilter() {
      this.set('nameFilter', '');
    },

    removePriceFilter() {
      this.set('priceFilter', 0);
    },

    removeRatingFilter() {
      this.set('ratingFilter', 0);
    },

    removeCuisineFilter() {
      this.set('cuisineFilter', []);
    },

    removeCityFilter() {
      this.set('city', '');
    },
  },
});
