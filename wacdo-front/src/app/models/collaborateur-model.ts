import { AffectationModel } from "./AffectationModel"

export type CollaborateurModel = {
    id: number
    nom: string
    prenom: string
    email: string
    datePremiereEmbauche: Date
    administrateur: boolean 
    affectations?: AffectationModel[]
}

export type CollaborateurList = Omit <CollaborateurModel, 'affecctation'> & {
    fonction?: string
    restaurant?: string 
}