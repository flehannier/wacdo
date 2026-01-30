import { AffectationModel } from "./affectation-model"
import { FonctionModel } from "./fonction-model"
import { RestaurantModel } from "./restaurant-model"
import { RoleModel } from "./role-model"

export type CollaborateurModel = {
    id: number
    nom: string
    prenom: string
    email: string
    motDePasse?: string
    datePremiereEmbauche: Date
    administrateur: boolean 
    affectations?: AffectationModel[]
    role: RoleModel
}

export type CollaborateurList = Omit <CollaborateurModel, 'affectation'> & {
    fonction?: string
    restaurant?: string 
}

export type CollaborateurRequest = Omit<CollaborateurModel, "affectations" | "role" | "datePremiereEmbauche"> & {
    roleName: string
}

export type CollaborateurResponse = CollaborateurRequest;