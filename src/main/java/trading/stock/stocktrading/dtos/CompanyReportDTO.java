package trading.stock.stocktrading.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyReportDTO {
    private Double priceCr;
    private String companyNameVi;
    private String code;
    private String industrylv2;
    private Double marketCapCr;
    private String quarterReportDate;
    private String floor;
    private String annualReportDate;
    private String id;
}
