import { CollaborateurModel } from "./collaborateur-model"
import { FonctionModel } from "./fonction-model"
import { RestaurantModel } from "./restaurant-model"

export type AffectationModel = {
 id: number,
 dateDebut: Date,
 dateFin?: Date,
 collaborateur: CollaborateurModel,
 fonction: FonctionModel,
 restaurant: RestaurantModel
}


export type AffectationRequest =  {
    id: number,
    dateDebut: Date,
    dateFin?: Date,
    collaborateurId: number,
    fonctionId: number,
    restaurantId: number,
};

export type  AffectationResponse =AffectationModel;