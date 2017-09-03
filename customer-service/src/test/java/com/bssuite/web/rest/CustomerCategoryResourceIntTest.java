package com.bssuite.web.rest;

import com.bssuite.CustomerServiceApp;

import com.bssuite.domain.CustomerCategory;
import com.bssuite.repository.CustomerCategoryRepository;
import com.bssuite.service.CustomerCategoryService;
import com.bssuite.service.dto.CustomerCategoryDTO;
import com.bssuite.service.mapper.CustomerCategoryMapper;
import com.bssuite.web.rest.errors.ExceptionTranslator;
import com.bssuite.service.dto.CustomerCategoryCriteria;
import com.bssuite.service.CustomerCategoryQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerCategoryResource REST controller.
 *
 * @see CustomerCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerServiceApp.class)
public class CustomerCategoryResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRADING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRADING_NAME = "BBBBBBBBBB";

    @Autowired
    private CustomerCategoryRepository customerCategoryRepository;

    @Autowired
    private CustomerCategoryMapper customerCategoryMapper;

    @Autowired
    private CustomerCategoryService customerCategoryService;

    @Autowired
    private CustomerCategoryQueryService customerCategoryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerCategoryMockMvc;

    private CustomerCategory customerCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerCategoryResource customerCategoryResource = new CustomerCategoryResource(customerCategoryService, customerCategoryQueryService);
        this.restCustomerCategoryMockMvc = MockMvcBuilders.standaloneSetup(customerCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerCategory createEntity(EntityManager em) {
        CustomerCategory customerCategory = new CustomerCategory()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .tradingName(DEFAULT_TRADING_NAME);
        return customerCategory;
    }

    @Before
    public void initTest() {
        customerCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerCategory() throws Exception {
        int databaseSizeBeforeCreate = customerCategoryRepository.findAll().size();

        // Create the CustomerCategory
        CustomerCategoryDTO customerCategoryDTO = customerCategoryMapper.toDto(customerCategory);
        restCustomerCategoryMockMvc.perform(post("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerCategory in the database
        List<CustomerCategory> customerCategoryList = customerCategoryRepository.findAll();
        assertThat(customerCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerCategory testCustomerCategory = customerCategoryList.get(customerCategoryList.size() - 1);
        assertThat(testCustomerCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomerCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerCategory.getTradingName()).isEqualTo(DEFAULT_TRADING_NAME);
    }

    @Test
    @Transactional
    public void createCustomerCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerCategoryRepository.findAll().size();

        // Create the CustomerCategory with an existing ID
        customerCategory.setId(1L);
        CustomerCategoryDTO customerCategoryDTO = customerCategoryMapper.toDto(customerCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerCategoryMockMvc.perform(post("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerCategory> customerCategoryList = customerCategoryRepository.findAll();
        assertThat(customerCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerCategoryRepository.findAll().size();
        // set the field null
        customerCategory.setCode(null);

        // Create the CustomerCategory, which fails.
        CustomerCategoryDTO customerCategoryDTO = customerCategoryMapper.toDto(customerCategory);

        restCustomerCategoryMockMvc.perform(post("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerCategory> customerCategoryList = customerCategoryRepository.findAll();
        assertThat(customerCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get all the customerCategoryList
        restCustomerCategoryMockMvc.perform(get("/api/customer-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].tradingName").value(hasItem(DEFAULT_TRADING_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCustomerCategory() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get the customerCategory
        restCustomerCategoryMockMvc.perform(get("/api/customer-categories/{id}", customerCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.tradingName").value(DEFAULT_TRADING_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get all the customerCategoryList where code equals to DEFAULT_CODE
        defaultCustomerCategoryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the customerCategoryList where code equals to UPDATED_CODE
        defaultCustomerCategoryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get all the customerCategoryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCustomerCategoryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the customerCategoryList where code equals to UPDATED_CODE
        defaultCustomerCategoryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get all the customerCategoryList where code is not null
        defaultCustomerCategoryShouldBeFound("code.specified=true");

        // Get all the customerCategoryList where code is null
        defaultCustomerCategoryShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get all the customerCategoryList where name equals to DEFAULT_NAME
        defaultCustomerCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerCategoryList where name equals to UPDATED_NAME
        defaultCustomerCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get all the customerCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerCategoryList where name equals to UPDATED_NAME
        defaultCustomerCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get all the customerCategoryList where name is not null
        defaultCustomerCategoryShouldBeFound("name.specified=true");

        // Get all the customerCategoryList where name is null
        defaultCustomerCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByTradingNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get all the customerCategoryList where tradingName equals to DEFAULT_TRADING_NAME
        defaultCustomerCategoryShouldBeFound("tradingName.equals=" + DEFAULT_TRADING_NAME);

        // Get all the customerCategoryList where tradingName equals to UPDATED_TRADING_NAME
        defaultCustomerCategoryShouldNotBeFound("tradingName.equals=" + UPDATED_TRADING_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByTradingNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get all the customerCategoryList where tradingName in DEFAULT_TRADING_NAME or UPDATED_TRADING_NAME
        defaultCustomerCategoryShouldBeFound("tradingName.in=" + DEFAULT_TRADING_NAME + "," + UPDATED_TRADING_NAME);

        // Get all the customerCategoryList where tradingName equals to UPDATED_TRADING_NAME
        defaultCustomerCategoryShouldNotBeFound("tradingName.in=" + UPDATED_TRADING_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByTradingNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);

        // Get all the customerCategoryList where tradingName is not null
        defaultCustomerCategoryShouldBeFound("tradingName.specified=true");

        // Get all the customerCategoryList where tradingName is null
        defaultCustomerCategoryShouldNotBeFound("tradingName.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCustomerCategoryShouldBeFound(String filter) throws Exception {
        restCustomerCategoryMockMvc.perform(get("/api/customer-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].tradingName").value(hasItem(DEFAULT_TRADING_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCustomerCategoryShouldNotBeFound(String filter) throws Exception {
        restCustomerCategoryMockMvc.perform(get("/api/customer-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCustomerCategory() throws Exception {
        // Get the customerCategory
        restCustomerCategoryMockMvc.perform(get("/api/customer-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerCategory() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);
        int databaseSizeBeforeUpdate = customerCategoryRepository.findAll().size();

        // Update the customerCategory
        CustomerCategory updatedCustomerCategory = customerCategoryRepository.findOne(customerCategory.getId());
        updatedCustomerCategory
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .tradingName(UPDATED_TRADING_NAME);
        CustomerCategoryDTO customerCategoryDTO = customerCategoryMapper.toDto(updatedCustomerCategory);

        restCustomerCategoryMockMvc.perform(put("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerCategory in the database
        List<CustomerCategory> customerCategoryList = customerCategoryRepository.findAll();
        assertThat(customerCategoryList).hasSize(databaseSizeBeforeUpdate);
        CustomerCategory testCustomerCategory = customerCategoryList.get(customerCategoryList.size() - 1);
        assertThat(testCustomerCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomerCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerCategory.getTradingName()).isEqualTo(UPDATED_TRADING_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerCategory() throws Exception {
        int databaseSizeBeforeUpdate = customerCategoryRepository.findAll().size();

        // Create the CustomerCategory
        CustomerCategoryDTO customerCategoryDTO = customerCategoryMapper.toDto(customerCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerCategoryMockMvc.perform(put("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerCategory in the database
        List<CustomerCategory> customerCategoryList = customerCategoryRepository.findAll();
        assertThat(customerCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerCategory() throws Exception {
        // Initialize the database
        customerCategoryRepository.saveAndFlush(customerCategory);
        int databaseSizeBeforeDelete = customerCategoryRepository.findAll().size();

        // Get the customerCategory
        restCustomerCategoryMockMvc.perform(delete("/api/customer-categories/{id}", customerCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerCategory> customerCategoryList = customerCategoryRepository.findAll();
        assertThat(customerCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCategory.class);
        CustomerCategory customerCategory1 = new CustomerCategory();
        customerCategory1.setId(1L);
        CustomerCategory customerCategory2 = new CustomerCategory();
        customerCategory2.setId(customerCategory1.getId());
        assertThat(customerCategory1).isEqualTo(customerCategory2);
        customerCategory2.setId(2L);
        assertThat(customerCategory1).isNotEqualTo(customerCategory2);
        customerCategory1.setId(null);
        assertThat(customerCategory1).isNotEqualTo(customerCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCategoryDTO.class);
        CustomerCategoryDTO customerCategoryDTO1 = new CustomerCategoryDTO();
        customerCategoryDTO1.setId(1L);
        CustomerCategoryDTO customerCategoryDTO2 = new CustomerCategoryDTO();
        assertThat(customerCategoryDTO1).isNotEqualTo(customerCategoryDTO2);
        customerCategoryDTO2.setId(customerCategoryDTO1.getId());
        assertThat(customerCategoryDTO1).isEqualTo(customerCategoryDTO2);
        customerCategoryDTO2.setId(2L);
        assertThat(customerCategoryDTO1).isNotEqualTo(customerCategoryDTO2);
        customerCategoryDTO1.setId(null);
        assertThat(customerCategoryDTO1).isNotEqualTo(customerCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerCategoryMapper.fromId(null)).isNull();
    }
}
