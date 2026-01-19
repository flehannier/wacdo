import { AffectationModel } from "./affectation-model"
import { FonctionModel } from "./fonction-model"
import { RestaurantModel } from "./restaurant-model"

export type CollaborateurModel = {
    id: number
    nom: string
    prenom: string
    email: string
    motDePasse?: string
    datePremiereEmbauche: Date
    administrateur: boolean 
    affectations?: AffectationModel[]
}

export type CollaborateurList = Omit <CollaborateurModel, 'affecctation'> & {
    fonction?: string
    restaurant?: string 
}

export type CollaborateurEdit = Omit <CollaborateurModel, 'affecctation'> & {
    fonction?: FonctionModel
    restaurant?: RestaurantModel 
}