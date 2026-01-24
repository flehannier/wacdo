export type UserModel = {
    username: string
    motDePasse: string
    role: string
    accesToken: string
}

export type UserWithoutRoleAndToken = Omit<UserModel, 'role' | 'accesToken'>;