
describe('AuthGuard', () => {
  let guard: AuthGuard;
  let authService: jasmine.SpyObj<AuthService>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(() => {
    const authSpy = jasmine.createSpyObj('AuthService', ['getToken', 'getRole']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      providers: [
        AuthGuard,
        { provide: AuthService, useValue: authSpy },
        { provide: Router, useValue: routerSpy }
      ]
    });

    guard = TestBed.inject(AuthGuard);
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
  });

  // =====================================
  // CAS 1 - Token ou rôle manquant
  // =====================================
  it('should redirect to /login if token is missing', () => {
    authService.getToken.and.returnValue(null);
    authService.getRole.and.returnValue(null);

    const result = guard.canActivate(null as any);
    expect(result).toBeFalse();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });

  // =====================================
  // CAS 2 - Rôle USER → accès interdit
  // =====================================
  it('should redirect USER role to /login with error', () => {
    authService.getToken.and.returnValue('valid-token');
    authService.getRole.and.returnValue(Role.USER);

    const result = guard.canActivate(null as any);
    expect(result).toBeFalse();
    expect(router.navigate).toHaveBeenCalledWith(['/login'], { queryParams: { error: 'unauthorized' } });
  });

  // =====================================
  // CAS 3 - Rôle ADMIN → accès autorisé
  // =====================================
  it('should allow ADMIN role to access', () => {
    authService.getToken.and.returnValue('valid-token');
    authService.getRole.and.returnValue(Role.ADMIN);

    const result = guard.canActivate(null as any);
    expect(result).toBeTrue();
    expect(router.navigate).not.toHaveBeenCalled();
  });

  // =====================================
  // CAS 4 - Rôle inconnu → accès refusé
  // =====================================
  it('should deny access for unknown role', () => {
    authService.getToken.and.returnValue('valid-token');
    authService.getRole.and.returnValue('UNKNOWN' as Role);

    const result = guard.canActivate(null as any);
    expect(result).toBeFalse();
    expect(router.navigate).not.toHaveBeenCalled(); // pas de redirection spécifique pour rôle inconnu
  });
});
