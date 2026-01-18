import { CollaborateurModel } from "./collaborateur-model"
import { FonctionModel } from "./fonction-model"
import { RestaurantModel } from "./restaurant-model"

export type AffectationModel = {
 dateDebut: Date,
 dateFin: Date,
 collaborateur: CollaborateurModel,
 fonction: FonctionModel,
 restaurant: RestaurantModel
}