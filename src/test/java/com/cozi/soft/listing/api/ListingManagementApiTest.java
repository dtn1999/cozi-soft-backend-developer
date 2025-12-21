package com.cozi.soft.listing.api;

import com.cozi.soft.listing.BaseApiTest;
import com.cozi.soft.listing.RequestParameters;
import com.cozi.soft.listing.ResourceFixtureLoader;
import com.cozi.soft.listing.ServiceModuleTestConfig;
import com.cozi.soft.listing.config.ListingDomainConfiguration;
import com.cozi.soft.listing.core.ports.in.ListingManagement;
import com.cozisoft.ream.model.CreateSiteRequestDto;
import com.cozisoft.ream.model.PatchSiteRequestDto;
import com.cozisoft.ream.model.SiteCollectionDto;
import com.cozisoft.ream.model.SiteDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@AutoConfigureMockMvc
@Import({ListingDomainConfiguration.class, ServiceModuleTestConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListingManagementApiTest extends BaseApiTest {
    final double SITE_ANNUAL_INTEREST_RATE = 9.99;
    final long SITE_PRICE_PER_PARCEL = 10000000L;
    final long SITE_DOWN_PAYMENT_PER_PARCEL = 500000L;
    final int SITE_DEFAULT_LOAN_TERM_IN_MONTHS = 360;
    final long SITE_MONTHLY_TAXES_FEE = 8333L;
    final double SITE_PARCEL_AREA = 1.0;
    final long EXPECTED_SITE_MONTHLY_PAYMENT_PER_PARCEL = 91632L;

    @MockitoSpyBean
    private ListingManagement listingManagement;

    private CreateSiteRequestDto createSiteRequest() {
        return ResourceFixtureLoader.loadResourceAsString(
                        "classpath:fixtures/listing/CreateSiteRequest.json",
                        CreateSiteRequestDto.class
                )
                .name("Site " + RandomStringUtils.randomAlphanumeric(10));
    }

    private PatchSiteRequestDto patchSiteRequest() {
        return ResourceFixtureLoader.loadResourceAsString(
                        "classpath:fixtures/listing/PatchSiteRequest.json",
                        PatchSiteRequestDto.class
                )
                .annualInterestRate(SITE_ANNUAL_INTEREST_RATE)
                .pricePerParcel(SITE_PRICE_PER_PARCEL)
                .downPaymentPerParcel(SITE_DOWN_PAYMENT_PER_PARCEL)
                .defaultLoanTermInMonths(SITE_DEFAULT_LOAN_TERM_IN_MONTHS)
                .monthlyTaxesFee(SITE_MONTHLY_TAXES_FEE)
                .parcelArea(SITE_PARCEL_AREA);
    }


    @Test
    void test_create_site_should_succeed_for_super_admin() throws Exception {
        CreateSiteRequestDto request = createSiteRequest();
        SiteDto createdSite = createSite(request);

        assertThat(createdSite)
                .extracting(
                        SiteDto::getName,
                        SiteDto::getParcelArea,
                        SiteDto::getTotalSiteArea,
                        SiteDto::getNumberOfParcels
                )
                .contains(
                        request.getName(),
                        request.getParcelArea(),
                        request.getTotalSiteArea(),
                        request.getTotalSiteArea()
                );
    }

    @Test
    void test_get_site_by_id_should_succeed() throws Exception {
        CreateSiteRequestDto request = createSiteRequest();
        SiteDto createdSite = createSite(request);

        SiteDto site = getSiteById(createdSite.getId());

        assertThat(site).isEqualTo(createdSite);
    }

    @Test
    void test_list_sites_should_succeed() throws Exception {
        dbResetService.resetAll();
        CreateSiteRequestDto request = createSiteRequest();
        createSite(request);
        createSite(request.name("Another Site"));

        SiteCollectionDto sites = listSites();

        assertThat(sites.getData()).hasSize(2);
    }

    @Test
    void test_patch_site_by_id_should_succeed() throws Exception {
        CreateSiteRequestDto request = createSiteRequest();
        SiteDto createdSite = createSite(request);
        PatchSiteRequestDto patchRequest = patchSiteRequest().name("Updated Name");

        SiteDto patchedSite = patchSite(createdSite.getId(), patchRequest);

        assertThat(patchedSite.getName()).isEqualTo("Updated Name");
        assertThat(patchedSite.getAnnualInterestRate()).isEqualTo(SITE_ANNUAL_INTEREST_RATE);
    }

    @Test
    void test_delete_site_by_id_should_succeed() throws Exception {
        CreateSiteRequestDto request = createSiteRequest();
        SiteDto createdSite = createSite(request);

        deleteSite(createdSite.getId());

        SiteDto deletedSite = getSiteById(createdSite.getId());
        assertThat(deletedSite.getDeleted()).isTrue();
        assertThat(deletedSite.getState().getValue()).isEqualTo("ARCHIVED");
    }

    private SiteDto createSite(CreateSiteRequestDto request) {
        String path = "/listings/sites";
        return postReq(
                RequestParameters.withAuthentication(path)
                        .body(request)
                , HttpStatus.CREATED
                , new TypeReference<>() {
                }
        );
    }

    private SiteDto getSiteById(Long id) {
        String path = "/listings/sites/" + id;
        return getReq(
                RequestParameters.withAuthentication(path)
                , HttpStatus.OK
                , new TypeReference<>() {
                }
        );
    }

    private SiteCollectionDto listSites() {
        String path = "/listings/sites";
        return getReq(
                RequestParameters.withAuthentication(path)
                , HttpStatus.OK
                , new TypeReference<>() {
                }
        );
    }

    private SiteDto patchSite(Long id, PatchSiteRequestDto request) {
        String path = "/listings/sites/" + id;
        return patchReq(
                RequestParameters.withAuthentication(path)
                        .body(request)
                , HttpStatus.OK
                , new TypeReference<>() {
                }
        );
    }

    private void deleteSite(Long id) {
        String path = "/listings/sites/" + id;
        deleteReq(
                RequestParameters.withAuthentication(path)
                , HttpStatus.NO_CONTENT
        );
    }

}
