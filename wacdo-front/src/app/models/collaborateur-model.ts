import { AffectationModel } from "./affectation-model"
import { FonctionModel } from "./fonction-model"
import { RestaurantModel } from "./restaurant-model"
import { RoleModel } from "./role-model"

export type CollaborateurModel = {
    id?: number
    nom: string
    prenom: string
    email: string
    motDePasse?: string
    datePremiereEmbauche: Date
    administrateur: boolean 
    fonction: FonctionModel
    restaurant: RestaurantModel
    role: RoleModel
}

export type CollaborateurRequest = Omit<CollaborateurModel, "restaurant" | "fonction" | "role"> & {
    roleId: number
};