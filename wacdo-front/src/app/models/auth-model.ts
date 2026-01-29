export type AuthRequest = {
    email: string
    motDePasse: string
}

export type AuthResponse = {
    username: string
    motDePasse: string
    role: string
    accesToken: string
}

export type UserWithoutRoleAndToken = Omit<AuthResponse, 'role' | 'accesToken'>;