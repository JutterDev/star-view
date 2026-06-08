package datasources.catalog

import datacontracts.CatalogRepository
import models.catalog.CatalogObject
import models.catalog.ObjectType
import models.catalog.ObjectType.GALAXY
import models.catalog.ObjectType.NEBULA
import models.catalog.ObjectType.STAR
import models.catalog.ObjectType.STAR_GROUP
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStreamReader

class CsvCatalogRepository: CatalogRepository {

    private var catalog: List<CatalogObject> = emptyList()

    override suspend fun getAllItems(): List<CatalogObject> {
        if (catalog.isEmpty()) loadAllList()
        return catalog
    }

    private fun loadAllList() {
        val ngcList = mutableListOf<CatalogObject>()

        try {
            val inputStream =  CsvCatalogRepository::class.java.classLoader
                .getResourceAsStream("raw/NGC.csv")
                ?: throw IllegalStateException("Файл raw/ngc.csv не найден в ресурсах модуля data")


            val reader = BufferedReader(InputStreamReader(inputStream))

            val csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build()

            val csvParser = CSVParser(reader, csvFormat)

            var i = 0
            for (record in csvParser) {
                ngcList.add(
                    CatalogObject(
                        id = i,
                        name = record.get("Name"),
                        type = record.get("Type"),
                        superType = toType(record.get("Type")),
                        ra = record.get("RA"),
                        dec = record.get("Dec"),
                        constellation = record.get("Const"),
                        majAx = record.get("MajAx").toDoubleOrNull(),
                        minAx = record.get("MinAx").toDoubleOrNull(),
                        posAng = record.get("PosAng").toIntOrNull(),
                        bMag = record.get("B-Mag").toDoubleOrNull(),
                        vMag = record.get("V-Mag").toDoubleOrNull(),
                        jMag = record.get("J-Mag").toDoubleOrNull(),
                        hMag = record.get("H-Mag").toDoubleOrNull(),
                        kMag = record.get("K-Mag").toDoubleOrNull(),
                        surfBr = record.get("SurfBr").toDoubleOrNull(),
                        hubble = record.get("Hubble").takeIf { it.isNotBlank() },
                        pax = record.get("Pax").toIntOrNull(),
                        pmRa = record.get("Pm-RA").toDoubleOrNull(),
                        pmDec = record.get("Pm-Dec").toDoubleOrNull(),
                        radVel = record.get("RadVel").toIntOrNull(),
                        redshift = record.get("Redshift").toDoubleOrNull(),
                        cstarUMag = record.get("Cstar U-Mag").toDoubleOrNull(),
                        cstarBMag = record.get("Cstar B-Mag").toDoubleOrNull(),
                        cstarVMag = record.get("Cstar V-Mag").toDoubleOrNull(),
                        m = record.get("M").toIntOrNull(),
                        ngc = record.get("NGC").toIntOrNull(),
                        ic = record.get("IC").toIntOrNull(),
                        cstarNames = record.get("Cstar Names").takeIf { it.isNotBlank() },
                        identifiers = record.get("Identifiers").takeIf { it.isNotBlank() },
                        commonNames = record.get("Common names").takeIf { it.isNotBlank() },
                        nedNotes = record.get("NED notes").takeIf { it.isNotBlank() },
                        openNgcNotes = record.get("OpenNGC notes").takeIf { it.isNotBlank() },
                        sources = record.get("Sources").takeIf { it.isNotBlank() }
                    )
                )
                i++
            }

            csvParser.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        catalog = ngcList
    }

    private fun toType(code: String): ObjectType {
        return when (code.trim().uppercase()) {
            "G", "GPAIR", "GTRPL", "GGROUP", "GCL" -> GALAXY
            "RFN", "EMN", "HII", "PN", "SNR", "DARKN", "CL+N" -> NEBULA
            "OCL", "*ASS", "AST", "*GROUP" -> STAR_GROUP
            "*", "**" -> STAR
            else -> ObjectType.UNKNOW
        }
    }
}