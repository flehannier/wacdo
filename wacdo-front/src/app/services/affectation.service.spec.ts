
describe('AffectationService', () => {
  let service: AffectationService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AffectationService],
    });

    service = TestBed.inject(AffectationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Vérifie qu'il n'y a pas de requêtes en attente
  });

  // =====================================
  // TEST listAffectations()
  // =====================================
  it('should return a list of affectations', () => {
    const mockAffectations: AffectationModel[] = [
      { id: 1, collaborateurId: 1, restaurantId: 1, fonctionId: 1, dateDebut: '2024-01-01', dateFin: null },
      { id: 2, collaborateurId: 2, restaurantId: 2, fonctionId: 2, dateDebut: '2024-02-01', dateFin: null }
    ];

    service.listAffectations().subscribe(affects => {
      expect(affects.length).toBe(2);
      expect(affects).toEqual(mockAffectations);
    });

    const req = httpMock.expectOne(environment.apiUrl + '/affectation');
    expect(req.request.method).toBe('GET');
    req.flush(mockAffectations); // Mock réponse serveur
  });

  // =====================================
  // TEST save()
  // =====================================
  it('should post an affectation and return response', () => {
    const request: AffectationRequest = {
      collaborateurId: 1,
      restaurantId: 1,
      fonctionId: 1,
      dateDebut: '2024-01-01',
      dateFin: null
    };

    const mockResponse: AffectationResponse = {
      id: 1,
      message: 'Affectation créée'
    };

    service.save(request).subscribe(res => {
      expect(res).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(environment.apiUrl + '/affectation');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(request);
    req.flush(mockResponse);
  });

  // =====================================
  // TEST delete() - CONFIRMED
  // =====================================
  it('should delete affectation when confirmed', () => {
    spyOn(window, 'confirm').and.returnValue(true); // Simule la confirmation utilisateur
    const mockDeleted: AffectationModel = {
      id: 1,
      collaborateurId: 1,
      restaurantId: 1,
      fonctionId: 1,
      dateDebut: '2024-01-01',
      dateFin: null
    };

    service.delete(1)?.subscribe(res => {
      expect(res).toEqual(mockDeleted);
    });

    const req = httpMock.expectOne(environment.apiUrl + '/affectation/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(mockDeleted);
  });

  // =====================================
  // TEST delete() - CANCELLED
  // =====================================
  it('should not call delete if user cancels', () => {
    spyOn(window, 'confirm').and.returnValue(false);

    const result = service.delete(1);
    expect(result).toBeUndefined();
  });
});
