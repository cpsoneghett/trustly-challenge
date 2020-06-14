package com.trustly.challenge.api.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table( name = "gh_file" )
@Cacheable( "GitHubFileData" )
public class GitHubFileData {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( name = "file_name" )
	private String fileName;

	@Column( name = "extension" )
	private String extension;

	@Column( name = "total_lines" )
	private String totalLines;

	@Column( name = "not_empty_lines" )
	private String notEmptyLines;

	@Column( name = "file_size" )
	private String fileSize;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "id_repository" )
	private GitHubRepositoryData repository;

	public GitHubFileData( GitHubRepositoryData ghrd, String fileName, String extension, String totalLines, String notEmptyLines, String fileSize ) {
		super();
		this.repository = ghrd;
		this.fileName = fileName;
		this.extension = extension;
		this.totalLines = totalLines;
		this.notEmptyLines = notEmptyLines;
		this.fileSize = getSizeInBytes( fileSize );
	}

	private String getSizeInBytes( String fileSize ) {

		String[] s = fileSize.split( " " );
		BigDecimal actualSize = new BigDecimal( s[ 0 ] );

		if ( s[ 1 ].contains( "K" ) )
			actualSize = actualSize.multiply( new BigDecimal( 1024 ) );
		else if ( s[ 1 ].contains( "M" ) )
			actualSize = actualSize.multiply( new BigDecimal( 1024 * 1024 ) );
		else if ( s[ 1 ].contains( "G" ) )
			actualSize = actualSize.multiply( new BigDecimal( 1024 * 1024 * 1024 ) );

		return actualSize.toString() + " Bytes";

	}

}
