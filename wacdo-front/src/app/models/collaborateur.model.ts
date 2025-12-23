import { RestaurantModel } from "./restaurant.model"

export class CollaborateurModel {
    id?: number
    nom?: string
    prenom?: string
    email?: string
    datePremiereEmbauche?: Date = new Date()
    administrateur?: boolean = false
    restaurant?: RestaurantModel = new RestaurantModel()
}