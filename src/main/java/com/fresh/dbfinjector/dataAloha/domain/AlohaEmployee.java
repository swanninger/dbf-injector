package com.fresh.dbfinjector.dataAloha.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Employee")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class AlohaEmployee {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(unique=true)
    private Integer number;

    @Column(unique=true)
    @EqualsAndHashCode.Include
    private String bohUser;

    @EqualsAndHashCode.Include
    @ToString.Include
    private String firstName;

    @EqualsAndHashCode.Include
    @ToString.Include
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "FK_Owner")
    private Owner owner;

    @ToString.Include
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeJob> employeeJobs = new ArrayList<>();

    @OneToMany(mappedBy = "employee", orphanRemoval = true)
    private List<EmployeePreference> employeePreference = new ArrayList<>();

    @OneToMany(mappedBy = "employee", orphanRemoval = true)
    private List<EmployeeBOHPasswordHistory> employeeBOHPasswordHistory = new ArrayList<>();

    private Integer SystemAccess;

    @ManyToOne
    @JoinColumn(name = "FK_SecurityRole")
    private SecurityRole SecurityRole;



    private byte[] encryptedTaxIdentifier;

    private Integer employmentStatus = 1;



    private String secondaryEmployeeNumber = "0";

    private UUID DataVersion = UUID.randomUUID();//6

    private String MiddleName = "";
    private String FormerName = "";
    private String NickName = "";
    private String address1 = "";
    private String address2 = "";
    private String address3 = "";
    private String address4 = "";
    private String city = "";
    private String postalCode = "";
    private String phoneNUmber = "";
    private String alternatePhoneNumber = "";
    private String code1 = "";
    private String code2 = "";
    private String code3 = "";
    private String code4 = "";
    private String menuLinkNumber = "";
    private String alienNumber = "";

    // NonNullable but useless fields
    private int disabilityStatus = 0; // 0
    private boolean doNotImportTimePunches = false; // False
    private int contactRelationship = 0; // 0
    private int employmentReason = 0; // 0
    private boolean exemptionFromWithholding = false; // False
    private int federalAllowances = 0; // 0
    private float federalWithholding = 0; // 0
    private int federalMaritalStatus = 0; // 0
    private int stateAllowances = 0; // 0
    private float stateWithholding = 0; //0
    private int stateTaxStatus = 0; //0
    private int localAllowances = 0; //0
    private float localWithholding = 0; //0
    private int citizenStatus = 0; //0
    private int documentATitle = 0; //0
    private int documentBTitle = 0; //0
    private int documentCTitle = 0; //0
    private boolean i9HardCopy = false; //false
    private String liquorCertificationNumber = ""; // ""
    private int a4AnnualIncome = 0; //0
    private int a4MoreThanWithholding = 0; //0
    private int a4LessThanWithholding = 0; //0
    private int languagePreference = 0;//0
    private String WorkPermitNumber = "";//""
    private int VacationPayPercent = 0;//0
    private int BankNumber = 0;//0
    private int BranchNumber = 0;//0
    private String AccountNumber = "";// ""
    private int PersonalTaxCredit = 0;//0
    private float AdditionalFederalTax = 0;//0
    private float AdditionalProvinceTax = 0;//0
    private int ProvinceExempt = 0;//0
    private int K4AllowanceRate = 0;//0
    private int K4NumberAllowances = 0;//0
    private float K4Withholding = 0;//0
    private boolean K4ExemptFromWithholding = false;//false
    private String EmailAddress = "";//""
    private String SmsAddress = "";// ""
//    private String BOHPasswordHash = "";//""
    private boolean UseDallasKey = false;//false
    private String PasswordHash = "";//""
    private String MagcardPasswordHash = "";//""
    private String DallasKeyPasswordHash = "";//""
    private int FailedLoginCount = 0;//0

    public void addEmployeeJob(EmployeeJob employeeJob) {
        employeeJobs.add(employeeJob);

        employeeJob.setEmployee(this);
        employeeJob.setOwner(this.owner);
    }
}
