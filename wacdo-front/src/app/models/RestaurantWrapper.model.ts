import { RestaurantModel } from "./restaurant.model";

export class RestaurantWrapper {
    _embedded! : { restaurants : RestaurantModel[]}
}